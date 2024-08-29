package ssd.uz.llc2.service.admin;


import ssd.uz.llc2.dto.ResponseDto;
import ssd.uz.llc2.dto.form.CreateGroupForm;

import java.util.UUID;

public interface    GroupService {
    ResponseDto save(CreateGroupForm form) throws Exception;

    ResponseDto edit(Long id, CreateGroupForm form) throws Exception;

    ResponseDto delete(Long id) throws Exception;


    ResponseDto search(String searching, int page, int size) throws Exception;

    ResponseDto findAll(int page, int size) throws Exception;

    ResponseDto findGroupsByTeacherId(UUID teacherId) throws Exception;

    ResponseDto getGroupsAndTeacherByStudentId(UUID userId) throws Exception;

    ResponseDto findAllByCourseId(Long courseId, int page, int size) throws Exception;

    ResponseDto addStudentToGroup(UUID studentId, Long groupId) throws Exception;
//    ResponseDto groupsList();

    ResponseDto removeStudentFromGroup(UUID studentId, Long groupId);

    ResponseDto getGroupIdAndName();

}
