package io.moblie.platform.comment;

import java.util.Date;
import java.util.List;

public class CommentMain {
    public static void main(String[] args) {
        // 1. 전체 댓글 조회
        System.out.println("\n전체 댓글 조회\n------------------------");
        List<Comment> commentList = CommentService.selectAll();
        for (Comment comment : commentList) {
            System.out.println(comment);
        }

        // 2. ID로 댓글 조회
        System.out.println("\nID로 댓글 조회 - ID 1\n------------------------");
        Comment commentById = CommentService.selectById(1);
        if (commentById != null) {
            System.out.println(commentById);
        } else {
            System.out.println("ID 1에 해당하는 댓글이 없습니다.");
        }

        // 3. 댓글 추가
        System.out.println("\n새로운 댓글 추가\n------------------------");
        int newCommentId = CommentService.insert("새로운 댓글입니다!", new Date(), "user01", 1);
        if (newCommentId > 0) {
            System.out.println("새 댓글이 추가되었습니다. ID: " + newCommentId);
        } else {
            System.out.println("댓글 추가에 실패했습니다.");
        }

        // 4. 댓글 수정
        System.out.println("\n댓글 수정\n------------------------");
        if (CommentService.update(newCommentId, "수정된 댓글입니다!", new Date()) > 0) {
            System.out.println("댓글이 수정되었습니다.");
        } else {
            System.out.println("댓글 수정에 실패했습니다.");
        }

        // 5. 댓글 삭제
        System.out.println("\n댓글 삭제\n------------------------");
        if (CommentService.deleteById(newCommentId) > 0) {
            System.out.println("댓글이 삭제되었습니다.");
        } else {
            System.out.println("댓글 삭제에 실패했습니다.");
        }
    }
}
