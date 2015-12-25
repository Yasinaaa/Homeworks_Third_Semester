package ru.samples.itis.rxpractice.tasks;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.List;

import ru.samples.itis.rxpractice.content.Person;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class HomeTask {

    /**
     * TODO : 1
     *
     * Rewrite all methods using lambdas, method references and etc (if not done yet)
     */

    /**
     * TODO : 2
     *
     * compare all items in observables, put result sequence into one integer observable
     */
    @NonNull
    public static <T> Observable<Integer> compareObservableItems(Observable<T> first,
                                                                 Observable<T> second,
                                                                 Comparator<T> comparator) {
        Observable<Integer> mObservable = Observable.zip(first, second, comparator::compare);
        return mObservable;
    }

}


