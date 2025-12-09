package board.backend.Auction.Domain.Model.Value;

import board.backend.Auction.Domain.Model.Enum.PropertyType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 부동산 정보
 */
@Getter
@EqualsAndHashCode
public class Property {
    private final String address;
    private final PropertyType type;
    private final double area;
    private final String description;
    private final List<String> imageUrls;

    private Property(String address, PropertyType type, double area,
                     String description, List<String> imageUrls) {

        validateAddress(address);
        validateArea(area);
        validateDescription(description);

        this.address= address;
        this.type = type;
        this.area = area;
        this.description = description;
        this.imageUrls = imageUrls != null ? new ArrayList<>(imageUrls) : new ArrayList<>();
    }

    public static Property of(String address, PropertyType type, double area,
                              String description, List<String > imageUrls) {
        return new Property(address, type, area ,description ,imageUrls);
    }

    private void validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("주소는 필수 입니다.");
        }

        if (address.length() > 200) {
            throw new IllegalArgumentException("주소는 200자를 초과할수가 없습니다.");
        }
    }

    private void validateArea(double area) {
        if (area <= 0) {
            throw new IllegalArgumentException("면적은 0보다 커야합니다.");
        }
        if (area > 10000) {
            throw new IllegalArgumentException("면적이 너무 큽니다.");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("설명은 필수입니다.");
        }
        if (description.length()> 2000) {
            throw new IllegalArgumentException("설명은 2000자를 초과 할 수 없습니다.");
        }
    }


    //평수로 변환
    public double getAreaInPyeong() {
        return area / 3.3058;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %.2fm)", address ,type.getDescription(), area);
    }
}
