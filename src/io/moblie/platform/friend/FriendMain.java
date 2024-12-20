package io.moblie.platform.friend;

import java.util.List;

public class FriendMain {
    public static void main(String[] args) {
        System.out.println("\n전체 친구 관계 정보 검색하기\n------------------------------------------------------------------------------------");
        List<Friend> friendList = FriendService.selectAll();
        for (Friend friend : friendList) {
            System.out.println(friend);
        }
        System.out.println("\n사용자 id와 친구id 전체 조회하기\n--------------------------------------------------------------------------------");
        String userId = "user02";
        List<Friend> friendshipList = FriendService.selectById(userId);
        if (friendshipList != null) {
            System.out.println(userId + "의 친구 전체 조회하기");
            for (Friend friend : friendshipList) {
                System.out.println(friend);
            }
        } else {
            System.out.println("친구 관계가 아닙니다.");
        }

        System.out.println("\n친구 관계 추가하기\n--------------------------------------------------------------------------------------------");
        if (FriendService.insert("user02", "user01") > 0) {
            Friend friendship = FriendService.selectFriendshipById("user02", "user01");
            if(friendship != null) {
                System.out.println("친구 관계가 성공적으로 이뤄졌습니다.");
            } else{
                System.out.println("존재하지 않는 친구 관계입니다.");
            }
        } else {
            System.out.println("이미 존재하는 친구 관계입니다.");
        }

        System.out.println(
                "\n친구 관계 삭제하기  - user03,user01\n-------------------------------------------------------------------------------------");
        if (FriendService.deleteById("user03", "user01") > 0) {
            Friend friendship = FriendService.selectFriendshipById("user03", "user01");
            if (friendship != null) {
                System.out.println("아직 친구 관계가 삭제되지 않았습니다.");
            } else {
                System.out.println("삭제 성공했습니다.");
            }
        } else {
            System.out.println("삭제에 실패했습니다.");
        }
    }
}

