package com.djdapz.stubhub.repository;

import com.djdapz.stubhub.domain.ProcessedListing;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.djdapz.stubhub.util.Random.randomProcessedListing;
import static lombok.AccessLevel.PRIVATE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ListingRepositoryTest {

    JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

    ListingRepository subject = new ListingRepository(jdbcTemplate);
    private ProcessedListing listing = randomProcessedListing();

    @Test
    public void shouldCallJdbcTemplateUpdateWhenSaving() {

        subject.saveListing(listing);
        verify(jdbcTemplate).update(
                insertSql(),
                listing.getListingId(),
                listing.getAsOfDate(),
                listing.getCurrentPrice().getAmount(),
                listing.getCurrentPrice().getCurrency(),
                listing.getListingPrice().getAmount(),
                listing.getListingPrice().getCurrency(),
                listing.getSectionId(),
                listing.getQuantity(),
                listing.getZoneId(),
                listing.getIsGA(),
                listing.getScore(),
                listing.getRow(),
                listing.getSellerSectionName(),
                listing.getSectionName(),
                listing.getSeatNumbers(),
                listing.getZoneName(),
                listing.getSplitOption(),
                listing.getTicketSplit(),
                listing.getDirtyTicketInd());
    }

    private String insertSql() {
        return "INSERT INTO city(" +
                "  listing_id             " +
                "  as_of_date             " +
                "  current_price_amount  " +
                "  current_price_currency " +
                "  listing_price_amount   " +
                "  listing_price_currency " +
                "  sectionId              " +
                "  quantity               " +
                "  zoneId                 " +
                "  isGA                   " +
                "  score                  " +
                "  row                    " +
                "  sellerSectionName      " +
                "  sectionName            " +
                "  seatNumbers            " +
                "  zoneName               " +
                "  splitOption            " +
                "  ticketSplit            " +
                "  dirtyTicketInd         " +
                ") VALUES(? ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    }
}