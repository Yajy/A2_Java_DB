package com.example.javamysql.grpc;


import com.example.javamysql.dto.StudentDTO;
import com.example.javamysql.mapper.StudentMapper;
import com.example.javamysql.model.Student;
import com.example.javamysql.service.StudentServiceImpl;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import student.StudentMessage;
import net.devh.boot.grpc.server.service.GrpcService;
import student.AddStudentRequest;
import student.AddStudentResponse;
import student.GetStudentRequest;
import student.GetStudentResponse;
import student.StudentServiceGrpc.StudentServiceImplBase;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class StudentGrpcService extends StudentServiceImplBase {

    private final StudentServiceImpl studentService;

    @Override
    public void createStudent(AddStudentRequest request,
                           StreamObserver<AddStudentResponse> responseObserver) {
        log.info("Received gRPC request to add student: {}", request.getFullName());


    try {
        StudentDTO dto = StudentDTO.builder()
                .rollNumber(request.getRollNumber())
                .fullName(request.getFullName())
                .age(request.getAge())
                .address(request.getAddress())
                .courses(request.getCoursesList())
                .build();

        studentService.addStudent(dto);

        AddStudentResponse response = AddStudentResponse.newBuilder()
                .setMessage("Student added successfully")
                .setSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    } catch (Exception e) {
        log.error("Error adding student: {}", e.getMessage());
        AddStudentResponse response = AddStudentResponse.newBuilder()
                .setMessage("Failed to add student: " + e.getMessage())
                .setSuccess(false)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }



        AddStudentResponse response = AddStudentResponse.newBuilder()
                        .setMessage("Student added successfully")
                        .setSuccess(true)
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();


    }

    @Override
    public void getStudent(GetStudentRequest request,
                           StreamObserver<GetStudentResponse> responseObserver) {
        log.info("Received gRPC request to get student with roll number");

        try {
            var studentDTOList = studentService.getAllStudentsAsDTO();
            var responseBuilder = GetStudentResponse.newBuilder();
            for (StudentDTO dto : studentDTOList) {
                StudentMessage studentMessage  = StudentMessage.newBuilder()
                        .setRollNumber(dto.getRollNumber())
                        .setFullName(dto.getFullName())
                        .setAge(dto.getAge())
                        .setAddress(dto.getAddress())
                        .addAllCourses(dto.getCourses())
                        .build();
                responseBuilder.addStudents(studentMessage);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        }
        catch (Exception e) {
            log.error("Error retrieving students: {}", e.getMessage());
            responseObserver.onError(e);
        }



}}
