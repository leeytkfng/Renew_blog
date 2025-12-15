package board.backend.Auction.Infrastructure.Entity.Embedded;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Property Embedded Document
 * MongoDB에 저장
 */
@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@AllArgsConstructor
public class PropertyEmbedded {

    @Field("address")
    private String address;

    @Field("type")
    private String type; //ProperType의 name

    @Field("area")
    private double area;

    @Field("description")
    private String description;

    @Field("image_urls")
    private List<String> imageUrls;
}
