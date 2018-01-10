package de.fhro.inf.prg3.a12.icndb.suppliers;

import de.fhro.inf.prg3.a12.icndb.ICNDBApi;
import de.fhro.inf.prg3.a12.icndb.ICNDBService;
import de.fhro.inf.prg3.a12.model.JokeDto;
import de.fhro.inf.prg3.a12.model.ResponseWrapper;
import org.apache.commons.lang3.NotImplementedException;

import java.util.function.Supplier;

/**
 * Supplier implementation to retrieve all jokes of the ICNDB in a linear way
 * @author Peter Kurfer
 */

public final class AllJokesSupplier implements Supplier<ResponseWrapper<JokeDto>> {

    /* ICNDB API proxy to retrieve jokes */
    private final ICNDBApi icndbApi;
    private final ResponseWrapper<Integer> jokeAmount;
    private int currentJoke = 0;

    public AllJokesSupplier() {
        icndbApi = ICNDBService.getInstance();
        jokeAmount = icndbApi.getJokeCount().join();
        /* fetch the total count of jokes the API is aware of
         * to determine when all jokes are iterated and the counters have to be reset */
    }

    public ResponseWrapper<JokeDto> get() {
        if (++currentJoke > jokeAmount.getValue())
            currentJoke = 1;

        return icndbApi.getJoke(currentJoke).join();
        /* retrieve the next joke
         * note that there might be IDs that are not present in the database
         * you have to catch an exception and continue if no joke was retrieved to an ID
         * if you retrieved all jokes (count how many jokes you successfully fetched from the API)
         * reset the counters and continue at the beginning */
    }

}
