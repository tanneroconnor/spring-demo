package tanneroconnor.dev.springdemo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        // Check that email is unique
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email is already taken.");
        }
        studentRepository.save(student);
    }

    public void deleteStudentById(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("Student with id: " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId,
                              String firstName,
                              String lastName,
                              String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id " + studentId + " does not exist"));

        boolean firstNameIsValid = firstName != null &&
                                    firstName.length() > 0 &&
                                    !Objects.equals(student.getFirstName(), firstName);
        boolean lastNameIsValid = lastName != null &&
                                    lastName.length() > 0 &&
                                    !Objects.equals(student.getLastName(), lastName);
        boolean emailIsValid = email != null &&
                                email.length() > 0 &&
                                !Objects.equals(student.getEmail(), email);


        if (firstNameIsValid) {
            student.setFirstName(firstName);
        }

        if (lastNameIsValid) {
            student.setLastName(lastName);
        }

        if (emailIsValid) {
            // Check if email is already taken
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email is already taken.");
            }
            student.setEmail(email);
        }
    }

}
