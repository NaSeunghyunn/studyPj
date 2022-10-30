package com.woorizip.woorizip.util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageCode {
    NOT_EXIST_ERROR("E0001","해당 데이터가 존재하지 않습니다."),
    NOT_EXIST_DATA_ERROR("E0002","존재하지 않는 {0} 입니다."),
    LOWER_LAYER_MOD_ERROR("E0003","상위{0}는 하위{0}로 수정불가능합니다."),
    EXISTS_REGISTER_ERROR("E0004","이미 등록된 {0}입니다."),
    PROCESSING_DELETE_ERROR("E0005","사용중인 {0}는 삭제할 수 없습니다."),
    BAD_INPUT_ERROR("E0006", "잘못된 {0}를 입력하셨습니댜."),
    INTERNAL_SERVER_ERROR("S0001","서버가 원활하지 않습니다. 잠시후 다시 시도해주세요.");

    private final String code;
    private final String description;

    public String code() {
        return code;
    }
}
