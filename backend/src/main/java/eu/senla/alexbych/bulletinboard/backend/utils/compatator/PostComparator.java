package eu.senla.alexbych.bulletinboard.backend.utils.compatator;

import eu.senla.alexbych.bulletinboard.backend.dto.PostDTO;

import java.util.Comparator;

public class PostComparator implements Comparator<PostDTO> {
    @Override
    public int compare(PostDTO o1, PostDTO o2) {
        return Float.compare(o2.getUser().getRating(), o1.getUser().getRating());
    }
}
