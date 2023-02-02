package com.idle.imfine.controller;

import com.idle.imfine.common.annotation.LoginUser;
import com.idle.imfine.common.response.ResponseService;
import com.idle.imfine.common.result.Result;
import com.idle.imfine.data.dto.bamboo.request.RequestBambooDto;
import com.idle.imfine.data.dto.bamboo.response.ResponseBamboo;
import com.idle.imfine.data.dto.bamboo.response.ResponseBambooDetailDto;
import com.idle.imfine.data.dto.heart.request.RequestHeartDto;
import com.idle.imfine.service.bamboo.BambooService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bamboo")
@Slf4j
@RequiredArgsConstructor
public class BambooController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BambooController.class);
    private final BambooService bambooService;
    private final ResponseService responseService;

    @GetMapping("/list")
    public ResponseEntity<Result> getList(@RequestParam("filter") String filter, @LoginUser String uid, @PageableDefault(size=10) Pageable pageable){
        LOGGER.info("list api에 들어옴  {}", filter);
        List<ResponseBamboo> responseBamboos = bambooService.showList(filter, uid, pageable);
        return ResponseEntity.ok().body(responseService.getListResult(responseBamboos));
    }


    @GetMapping("/myactive")
    // 로그인 구현되면 loginuser 추가하기
    public ResponseEntity<Result> getMyActiveList(@RequestParam("filter") String filter, @LoginUser String uid, @PageableDefault(size=10) Pageable pageable){
        LOGGER.info("myactivelist api에 들어옴  {}", filter);

        List<ResponseBamboo> responseBamboos = bambooService.showMyList(filter, uid, pageable);
        return ResponseEntity.ok()
                .body(responseService.getListResult(responseBamboos));
    }

    @GetMapping("/detail/{bamboo-id}")
    // 로그인 구현되면 loginuser 추가하기
    public ResponseEntity<Result> getBambooDetail(@PathVariable("bamboo-id") long bambooId, @LoginUser String uid){
        LOGGER.info("getBamboodetail api에 들어옴  {}", bambooId);

        ResponseBambooDetailDto responseBambooDetailDto = bambooService.showBambooDetail(bambooId, uid);
        return ResponseEntity.ok()
                .body(responseService.getSingleResult(responseBambooDetailDto));
    }
    @PostMapping
    // 로그인 구현되면 loginuser 추가하기
    public ResponseEntity<Result> postBamboo(@RequestBody RequestBambooDto requestBamboo, @LoginUser String uid) {
        LOGGER.info("대나무 등록 api에 들어왔습니다.");
//        RequestBambooDto rbd = new RequestBambooDto(content, 1);
        requestBamboo.setWriterId(uid);
        bambooService.save(requestBamboo);
//        LOGGER.info("대나무 등록 api에 들어왔습니다. {}", rbd);
        return ResponseEntity.ok()
                .body(responseService.getSuccessResult());
    }
    @PostMapping("/like")
    // 로그인 구현되면 loginuser 추가하기
    public ResponseEntity<Result> postBambooLike(@RequestBody RequestHeartDto requestHeartDto, @LoginUser String uid) {

        bambooService.likeBamboo(requestHeartDto, uid);
//        LOGGER.info("대나무 좋아요 {}", rl );
        return ResponseEntity.ok()
                .body(responseService.getSuccessResult());
    }

    @DeleteMapping("/like/{bamboo-id}")
    // 로그인 구현되면 loginuser 추가하기
    // 검증을 서비스에서 해야하나?
    public ResponseEntity<Result> deleteBambooLike(@PathVariable("bamboo-id") long bambooId, @LoginUser String uid) {
        LOGGER.info("대나무 좋아요 삭제 api에 들어왔습니다. {}", bambooId);

        bambooService.deleteLikeBamboo(bambooId, uid);

        return ResponseEntity.ok()
                .body(responseService.getSuccessResult());
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Result> deleteBamboo() {
        bambooService.deleteBamboo();

        return ResponseEntity.ok()
                .body(responseService.getSuccessResult());

    }
}
