package com.wait.app.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wait.app.domain.entity.Attachment;
import com.wait.app.repository.AttachmentRepository;
import com.wait.app.utils.minio.ObjStorageService;
import com.wait.app.utils.minio.UploadAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author 天
 * @description: 附件接口实现
 */
@Service
public class AttachmentService{

    private final ObjStorageService minioService;

    private final AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentService(ObjStorageService minioService, AttachmentRepository attachmentRepository) {
        this.minioService = minioService;
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment uploadAttachment(MultipartFile multipartFile, String ownerType, String ownerId) {
        UploadAttachment uploadAttachment = minioService.storeFile(multipartFile);
        Attachment attachment = BeanUtil.copyProperties(uploadAttachment, Attachment.class);
        attachment.setOwnerType(ownerType);
        attachment.setOwnerId(ownerId);
        attachmentRepository.save(attachment);
        return attachment;
    }

    public List<Attachment> uploadAttachmentList(List<MultipartFile> multipartFileList, String ownerType, String ownerId) {
        List<UploadAttachment> uploadAttachmentList = minioService.storeFiles(multipartFileList);
        List<Attachment> attachments = BeanUtil.copyToList(uploadAttachmentList, Attachment.class);
        attachments.forEach(attachment -> {
            attachment.setOwnerType(ownerType);
            attachment.setOwnerId(ownerId);
        });
        attachmentRepository.saveBatch(attachments);
        return attachments;
    }

    public List<Attachment> getAttachment(String ownerType, String ownerId) {
        LambdaQueryWrapper<Attachment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Attachment::getOwnerType, ownerType);
        queryWrapper.eq(Attachment::getOwnerId, ownerId);
        return attachmentRepository.list(queryWrapper);
    }

    public List<Attachment> getAttachment(String ownerType, List<String> ownerIds) {
        LambdaQueryWrapper<Attachment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Attachment::getOwnerType, ownerType);
        queryWrapper.in(CollUtil.isNotEmpty(ownerIds),Attachment::getOwnerId, ownerIds);
        return attachmentRepository.list(queryWrapper);
    }

    public String getAttachmentUrl(String objName, Integer expiryTime) {
        return minioService.getFileUrl(objName, expiryTime);
    }

    public InputStream downloadAttachment(String objName) {
        return minioService.getFile(objName);
    }

    public boolean removeAttachment(String ownerType, String ownerId) {
        List<Attachment> attachmentList = this.getAttachment(ownerType, ownerId);
        List<String> attachmentIds = CollStreamUtil.toList(attachmentList, Attachment::getId);
        List<String> attachmentObjNames = CollStreamUtil.toList(attachmentList, Attachment::getObjName);
        minioService.removeList(attachmentObjNames);
        return attachmentRepository.removeBatchByIds(attachmentIds);
    }

    public boolean removeAttachmentById(String attachmentId) {
        Attachment attachment = attachmentRepository.getById(attachmentId);
        minioService.remove(attachment.getObjName());
        return attachmentRepository.removeById(attachmentId);
    }

    public boolean removeAttachmentByIds(List<String> attachmentIds) {
        List<Attachment> attachments = attachmentRepository.listByIds(attachmentIds);
        List<String> attachmentObjNames = CollStreamUtil.toList(attachments, Attachment::getObjName);
        minioService.removeList(attachmentObjNames);
        return attachmentRepository.removeBatchByIds(attachmentIds);
    }

    public Attachment updateAttachment(String attachmentId, MultipartFile multipartFile) {
        Attachment attachment = attachmentRepository.getById(attachmentId);
        minioService.remove(attachment.getObjName());

        UploadAttachment uploadAttachment = minioService.storeFile(multipartFile);
        Attachment newAttachment = BeanUtil.copyProperties(uploadAttachment, Attachment.class);

        newAttachment.setId(attachmentId);
        newAttachment.setCreateTime(attachment.getCreateTime());
        attachmentRepository.updateById(newAttachment);
        return newAttachment;
    }


}
