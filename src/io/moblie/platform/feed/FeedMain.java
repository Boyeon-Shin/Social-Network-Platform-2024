package io.moblie.platform.feed;

import java.util.Date;
import java.util.List;

public class FeedMain {
    public static void main(String[] args) {
        // 1. 전체 피드 정보 검색
        System.out.println("\n전체 피드 정보 검색\n -------------------------");
        List<Feed> feedList = FeedService.selectAll();
        for (Feed feed : feedList) {
            System.out.println(feed);
        }

        // 2. ID로 피드 검색하기
        System.out.println("\nID로 검색하기 - ID 1\n ------------------------");
        Feed feedById = FeedService.selectById(1);
        if (feedById != null) {
            System.out.println(feedById);
        } else {
            System.out.println("해당 ID에 해당하는 피드가 없습니다.");
        }

        // 3. 새로운 피드 추가 -> 추가 확인 검색
        System.out.println("\n새로운 피드 추가하기\n ------------------------");
        int newFeedId = FeedService.insert("새로운 피드 내용입니다!", new Date(), "user01");
        if (newFeedId > 0) {
            Feed newFeed = FeedService.selectById(newFeedId);
            if (newFeed != null) {
                System.out.println(newFeed);
            } else {
                System.out.println("새로운 피드가 존재하지 않습니다.");
            }
        } else {
            System.out.println("새로운 피드 추가에 실패했습니다.");
        }

        // 4. 피드 정보 수정 -> 추가 확인 검색
        System.out.println("\n피드 정보 수정하기\n ------------------------");
        if (FeedService.update(newFeedId, "수정된 피드 내용입니다.", new Date()) > 0) {
            Feed updatedFeed = FeedService.selectById(newFeedId);
            if (updatedFeed != null) {
                System.out.println(updatedFeed);
            } else {
                System.out.println("수정된 피드가 존재하지 않습니다.");
            }
        } else {
            System.out.println("피드 정보 수정에 실패했습니다.");
        }

        // 5. 피드 정보 삭제 -> 추가 확인 검색
        System.out.println("\n피드 정보 삭제하기\n ------------------------");
        if (FeedService.deleteById(newFeedId) > 0) {
            Feed deletedFeed = FeedService.selectById(newFeedId);
            if (deletedFeed != null) {
                System.out.println("피드가 삭제되지 않았습니다.");
            } else {
                System.out.println("피드 정보 삭제에 성공했습니다.");
            }
        } else {
            System.out.println("피드 정보 삭제에 실패했습니다.");
        }
    }
}

