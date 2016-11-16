package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class CommentNodeImpl implements CommentNode {
    private String authorName;
    private String authorSurname;

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    private Comment comment;
    List<CommentNode> children;
    public CommentNodeImpl(Comment comment, String name, String surname) {
        this.comment = comment;
        this.authorName = name;
        this.authorSurname = surname;
        this.children = new ArrayList<>();
    }

    @Override
    public Comment getComment() {
        return comment;
    }

    @Override
    public List<CommentNode> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<CommentNode> children) {
        this.children.addAll(children);
    }
}
