package cn.metsea.lotus.remote.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * Key Value Pair
 *
 * @param <L> L generic type
 * @param <R> R generic type
 */
@Setter
@Getter
public class Pair<L, R> {

    private L left;

    private R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Pair of(L left, R right) {
        return new Pair(left, right);
    }

}