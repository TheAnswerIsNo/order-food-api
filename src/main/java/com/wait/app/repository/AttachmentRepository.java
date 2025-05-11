package com.wait.app.repository;



import com.tangzc.mpe.base.repository.BaseRepository;
import com.wait.app.domain.entity.Attachment;
import com.wait.app.repository.mapper.AttachmentMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 天
 * Time: 2024/9/7 9:52
 */
@Repository
public class AttachmentRepository extends BaseRepository<AttachmentMapper, Attachment> {
}
