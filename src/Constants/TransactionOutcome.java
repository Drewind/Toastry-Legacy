package Constants;

/**
 * Monitors the stage of a transaction. Keeps track of where a transaction is within the system.
 *  * CREATED     - entity has been created.
 *  * PENDING     - sale entity has not been saved and is waiting to be processed.
 *  * COMPLETE    - sale has been finished, saved to DB, and approved.
 */
public enum TransactionOutcome {
    CREATED,
    PENDING,
    COMPLETED
}
