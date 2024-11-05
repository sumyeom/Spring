package com.example.memo.controller;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entitiy.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/memos") //prefix
public class MemoController {
    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto dto){
        // 식별자가 1씩 증가하도록 만듦
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;

        // 요청 받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());

        //inmemory DB 에 Memo 저장
        memoList.put(memoId, memo);

        return new MemoResponseDto(memo);
    }

    @GetMapping("/{id}")
    public MemoResponseDto findMemoById(@PathVariable Long id){
        Memo memo = memoList.get(id);

        return new MemoResponseDto(memo);
    }

    @PutMapping("/{id}")
    public MemoResponseDto udpateMemoById(
            @PathVariable Long id,
            @RequestBody MemoRequestDto dto
    ){
        Memo memo = memoList.get(id);

        memo.update(dto);

        return new MemoResponseDto(memo);
    }

    @DeleteMapping("/{id}")
    public void deleteMemo(
            @PathVariable Long id
    ){
        memoList.remove(id);
    }
}
