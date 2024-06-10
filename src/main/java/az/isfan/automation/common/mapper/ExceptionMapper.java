package az.isfan.automation.common.mapper;

import az.isfan.automation.common.dto.ErrorDto;
import az.isfan.automation.common.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExceptionMapper {
    private Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);

    public ResponseDto toResponseDto(List<ErrorDto> errorDtoList) {
        logger.info("toResponseDto: errorDtoList sieze = {}", errorDtoList.size());

        var responseDto = new ResponseDto(
                false,
                errorDtoList,
                null
        );
        return responseDto;
    }
}

