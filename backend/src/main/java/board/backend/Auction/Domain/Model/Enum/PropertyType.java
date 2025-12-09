package board.backend.Auction.Domain.Model.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PropertyType {
    APARTMENT("아파트"),
    VILLA("빌라"),
    OFFICETEL("오피스텔"),
    COMMERCIAL("상가"),
    BUILDING("빌딩"),
    LAND("토지");

    private final String description;
}
