package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class CommentNodeImpl implements CommentNode {
    Comment comment;
    List<CommentNode> children;
    public CommentNodeImpl(Comment comment) {
        this.comment = comment;
    }

    @Override
    public Comment getComment() {
        return comment;
    }

    @Override
    public List<CommentNode> getChildren() {
        return children;
    }
}
