package common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongSupplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

public class PageMaker {
	/**
	 * This helper method will turn a list of objects to a Page object
	 * @param <T> : generic object type
	 * @param page : Pageable object for page number and size
	 * @param contents : contents that needed to be pageable
	 * @return Page object of the supplied list of objects
	 */
	public static <T> Page<T> extractPage(Pageable page, List<T> contents) {
        int startIndex = page.getPageNumber() == 0 ? page.getPageNumber() : page.getPageNumber() + page.getPageSize();
        int toIndex = startIndex + page.getPageSize();
        toIndex = toIndex > contents.size() ? contents.size() : toIndex;

        final int total = contents.size();

        List<T> filteredContents;

        if (startIndex < contents.size()) {
            filteredContents = contents.subList(startIndex, toIndex);
        } else {
            filteredContents = new ArrayList<>();
        }

        LongSupplier totalSupplier = () -> {
            return total;
        };

        return PageableExecutionUtils.getPage(filteredContents, PageRequest.of(page.getPageNumber(), page.getPageSize()),
                totalSupplier);
    }
}
