
// Title: Completeable.java
// Author: Kevin Nard
// Interface that describes a completeable event

package base;

public interface Completable {

    // Set completion status to true
    void complete();

    // Return the completion status
    boolean isComplete();
}
