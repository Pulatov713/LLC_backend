package ssd.uz.llc2.service.student.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ssd.uz.llc2.db.domain.File;
import ssd.uz.llc2.db.domain.User;
import ssd.uz.llc2.db.domain.tasks.Homework;
import ssd.uz.llc2.db.domain.tasks.Task;
import ssd.uz.llc2.db.repository.student.HomeworkRepository;
import ssd.uz.llc2.db.repository.teacher.TaskRepository;
import ssd.uz.llc2.dto.ResponseDto;
import ssd.uz.llc2.helper.SecurityHelper;
import ssd.uz.llc2.service.file.FileService;
import ssd.uz.llc2.service.student.HomeworkService;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class HomeworkServiceImpl implements HomeworkService {
    @Autowired
    private FileService fileService;
    @Autowired
    private HomeworkRepository homeworkRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public ResponseDto saveHomework(UUID taskId, UUID homeworkId, MultipartFile file) throws Exception {
        try {
            User currentUser = SecurityHelper.getCurrentUser();
            if (currentUser == null) {
                throw new Exception("Current user doesn't exist");
            }
            if (taskId != null) {
                ResponseDto responseDto;
                File fileToSave;
                Task task;
                Optional<Task> taskOptional = taskRepository.findById(taskId);

                if (taskOptional.isPresent()) {
                    task = taskOptional.get();
                } else {
                    throw new Exception("Task doesn't exist");
                }

                if (task.getDeadline().after(new Date())) {
                    if (!file.isEmpty()) {
                        responseDto = fileService.save(file);
                        fileToSave = (File) responseDto.getData();
                    } else {
                        throw new Exception("File doesn't exist");
                    }
                    if (responseDto.isSuccess() && responseDto.getData() != null) {
                        Homework homework = new Homework();
                        if (homeworkId != null) {
                            Optional<Homework> optionalHomework = homeworkRepository.findById(homeworkId);
                            if (optionalHomework.isPresent()) {
                                homework = optionalHomework.get();
                                homework.setFile(fileToSave);
                                homework.setTask(task);
                                homework = homeworkRepository.save(homework);
                                if (homework.getFile() != null) {
                                    fileService.delete(homework.getFile().getPkey(), homework.getFile().getName());
                                    return new ResponseDto<>(true, "Homework updated successfully");
                                }
                            }
                            throw new Exception("Homework is not present");
                        } else {
                            homework.setTask(task);
                            homework.setStudent(currentUser);
                            homework.setFile(fileToSave);
                            homework = homeworkRepository.save(homework);
                            if (homework.getFile() != null) {
                                return new ResponseDto<>(true, "Homework saved successfully");
                            }
                        }
                        throw new Exception("An error occurred while saving the homework");
                    } else {
                        throw new Exception("An error occurred while saving the file");
                    }
                } else {
                    throw new Exception("Vazifa o'tib ketdi endi kech!");
                }
            } else {
                throw new Exception("An error occurred while defining task ");
            }
        } catch (Exception e) {
            return new ResponseDto<>(false, e.getMessage());
        }
    }
}
