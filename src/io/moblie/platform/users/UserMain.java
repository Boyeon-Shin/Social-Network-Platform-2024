package io.moblie.platform.users;

import java.util.List;

public class UserMain {
    public static void main(String[] args) {
        System.out.println("\n전체 사용자 정보 검색하기\n");
        List<Users> usersList = UserService.selectAll();
        for (Users user : usersList) {
            System.out.println(user);
        }

        System.out.println("\n사용자 번호로 검색하기\n");
        Users user01 = UserService.selectById("user01");
        if(user01 != null) {
            System.out.println(user01);
        } else {
            System.out.println("존재하지 않는 사용자");
        }

        System.out.println("\n사용자 정보 추가하기\n");
        if(UserService.insert("user06", "홍길동", "djuss@hdd.com", "Male", "123-456-7890") > 0) {
            Users user06 = UserService.selectById("user06");
            if(user06 != null) {
                System.out.println(user06);
            } else {
                System.out.println("존재하는 않는 사용자입니다.");
            }
        } else {
            System.out.println("user06 고객 추가 실패입니다.");
        }

        System.out.println("\n사용자 정보 수정하기\n");
        if(UserService.updateById("user03", "Aloha", "Aloha@example.com", "Male" , "010-3737-3838") > 0) {
            Users user03 = UserService.selectById("user03");
            if(user03 != null) {
                System.out.println(user03);
            } else {
                System.out.println("존재하지 않는 사용자");
            }
        } else {
            System.out.println("user03 고객 수정 실패");
        }


        System.out.println("\n사용자 정보 삭제하기  - user03\n------------------------------------------------------------------------------------");
        if(UserService.deleteById("user03") > 0) {
            Users user03 = UserService.selectById("user03");
            if(user03 != null) {
                System.out.println("user03 exist");
            } else {
                System.out.println("삭제 성공");
            }
        } else {
            System.out.println("삭제에 실패");
        }
    }
}
