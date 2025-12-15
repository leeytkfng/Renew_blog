package board.backend.Auction.Infrastructure.Repository.Impl;

import board.backend.Auction.Domain.Model.Aggregate.Auction;
import board.backend.Auction.Domain.Model.Enum.AuctionStatus;
import board.backend.Auction.Domain.Model.Value.AuctionId;
import board.backend.Auction.Domain.Repository.AuctionRepository;
import board.backend.Auction.Infrastructure.Entity.AuctionMongoEntity;
import board.backend.Auction.Infrastructure.Mapper.AuctionMapper;
import board.backend.Auction.Infrastructure.Repository.AuctionMongoRepository;
import board.backend.Auth.Domain.Model.Vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Auction Repository 구현체
 * Domain Repository Interface 구현
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class AuctionRepositoryImpl  implements AuctionRepository {

    private final AuctionMongoRepository mongoRepository;
    private final AuctionMapper auctionMapper;

    @Override
    public Auction save(Auction auction) {
        log.debug(">>> [Repository] save: auctionId={}", auction.getId().getValue());

        AuctionMongoEntity entity = auctionMapper.toMongoEntity(auction);
        AuctionMongoEntity saved = mongoRepository.save(entity);

        log.debug(">>> [Repository] save 완료: auctionId={}", saved.getId());
        return auctionMapper.toDomain(saved);
    }

    @Override
    public Optional<Auction> findById(AuctionId id) {
        log.debug(">>> [Repository] findById: auctionId={}", id.getValue());


        return mongoRepository.findById(id.getValue())
                .map(entity ->  {
                    log.debug(">>> [Repository] 경매 찾음: auctionId={}", entity.getId());
                    return auctionMapper.toDomain(entity);
                });
    }

    @Override
    public List<Auction> findAll() {
        log.debug(">>> [Repository] findAll");


        return mongoRepository.findAll().stream()
                .map(auctionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Auction> findAllActive() {
        log.debug(">>> [Repository] findAllActive");


        return mongoRepository.findByStatus(AuctionStatus.ACTIVE.name()).stream()
                .map(auctionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Auction> findByStatus(AuctionStatus status) {
        return null;
    }

    @Override
    public List<Auction> findBySellerId(UserId userId) {
        return null;
    }

    @Override
    public List<Auction> findByBidderId(UserId userId) {
        return null;
    }

    @Override
    public void delete(AuctionId id) {

    }

    @Override
    public boolean existsById(AuctionId id) {
        return false;
    }
}
