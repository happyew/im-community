package com.example.imcommunity.util;

import lombok.Data;
import org.springframework.data.domain.Page;

public class PageUtil {
    public static <T> PageDetail getPageDetail(Page<T> currentPage, int currentIndex) {
        PageDetail pageDetail = new PageDetail();
        int totalPages = currentPage.getTotalPages();
        int startIndex = 0;
        int endIndex = 0;
        if (totalPages <= 10) {
            startIndex = 1;
            endIndex = totalPages;
        } else {
            if (currentIndex <= 6) {
                startIndex = 1;
                endIndex = 10;
            } else {
                if (totalPages - currentIndex > 4) {
                    endIndex = currentIndex + 4;
                } else {
                    endIndex = totalPages;
                }
                startIndex = endIndex - 9;
            }
        }
        pageDetail.setStartNumber(startIndex);
        pageDetail.setEndNumber(endIndex);
        pageDetail.setCurrentNumber(currentIndex);
        if (currentPage.hasPrevious()) {
            pageDetail.setPreviousNumber(currentPage.previousPageable().getPageNumber() + 1);
        }
        if (currentPage.hasNext()) {
            pageDetail.setNextNumber(currentPage.nextPageable().getPageNumber() + 1);
        }
        return pageDetail;
    }

    @Data
    public static class PageDetail {
        private Integer startNumber;
        private Integer endNumber;
        private Integer previousNumber = -1;
        private Integer currentNumber;
        private Integer nextNumber = -1;
    }
}
