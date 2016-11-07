package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface CommentNode {
    public Comment getComment();
    public List<CommentNode> getChildren();
}
