package com.fiveguys.robocar.swaggertest;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "{도메인명}", description = "설명 필요 시 작성")
public class ExampleController {

    @Operation(summary = "문자열 이어붙이기") // 메소드 설명
    @Parameters(value = { // 파라미터 설명
            @Parameter(name = "str1", description = "첫 번째 문자열"),
            @Parameter(name = "str2", description = "두 번째 문자열")})
    @ApiResponses(value = { // 응답 설명
            @ApiResponse(responseCode = "200", description = "이어붙이기 성공"),
            @ApiResponse(responseCode = "400", description = "클라이언트 에러1"),
            @ApiResponse(responseCode = "500", description = "서버 에러")})
    @GetMapping("/swagger/concat")
    public ResponseEntity concat(String str1, String str2) {
        return ResponseApi.ok(str1 + str2);
    }

    @Operation(summary = "문자열 복사하기") // 메소드 설명
    @ApiResponses(value = { // 응답 설명
            @ApiResponse(responseCode = "200", description = "복사 성공"),
            @ApiResponse(responseCode = "400", description = "클라이언트 에러1"),
            @ApiResponse(responseCode = "500", description = "서버 에러")})
    @GetMapping("/swagger/copy")
    public ResponseEntity copy(@RequestBody ExamDto examDto) {
        return ResponseApi.ok(examDto.getName() + examDto.getAge());
    }

    @Operation(summary = "pathvaliable 테스트") // 메소드 설명
    @Parameter(name = "id", description = "아이디")
    @GetMapping("/swagger/{id}")
    public ResponseEntity copy(@PathVariable String id) {
        return ResponseApi.ok(id);
    }
}
