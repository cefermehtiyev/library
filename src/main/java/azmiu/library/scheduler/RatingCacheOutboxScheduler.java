package azmiu.library.scheduler;

import azmiu.library.dao.repository.RatingCacheOutboxRepository;
import azmiu.library.service.abstraction.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RatingCacheOutboxScheduler {

    private final RatingCacheOutboxRepository ratingCacheOutboxRepository;
    private final CacheService cacheService;

    @Scheduled(cron = "5 * * * * *")
    @SchedulerLock(name = "processOutboxEntries", lockAtLeastFor = "PT1M", lockAtMostFor = "PT3M")
    @Transactional
    public void processOutboxEntries() {
        var unprocessedEntries = ratingCacheOutboxRepository.findByProcessedFalse();

        unprocessedEntries.forEach(
                cacheOutboxEntity ->
                {
                    try {
                        cacheService.save(cacheOutboxEntity.getBookInventoryId(), cacheOutboxEntity.getVoteCount(), cacheOutboxEntity.getAverageRating());
                        cacheOutboxEntity.setProcessed(true);
                        ratingCacheOutboxRepository.save(cacheOutboxEntity);
                        log.info("Outbox entry successfully processed and marked. ID: {}", cacheOutboxEntity.getId());

                    } catch (Exception ex) {
                        log.error("Error occurred while processing outbox entry. ID: {} - Will retry.",
                                cacheOutboxEntity.getId(), ex);

                    }
                }
        );

    }

}
