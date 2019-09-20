package austeretony.oxygen_trade.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import austeretony.oxygen_core.client.api.OxygenHelperClient;
import austeretony.oxygen_core.common.persistent.AbstractPersistentData;
import austeretony.oxygen_core.common.util.StreamUtils;
import austeretony.oxygen_trade.common.config.TradeConfig;
import austeretony.oxygen_trade.common.main.TradeMain;

public class OffersContainerClient extends AbstractPersistentData {

    private final Map<Long, PlayerOfferClient> offers = new ConcurrentHashMap<>();

    public int getOffersAmount() {
        return this.offers.size();
    }

    public Set<Long> getOfferIds() {
        return this.offers.keySet();
    }

    public Collection<PlayerOfferClient> getOffers() {
        return this.offers.values();
    }

    public PlayerOfferClient getOffer(long offerId) {
        return this.offers.get(offerId);
    }

    public void addOffer(PlayerOfferClient offer) {
        this.offers.put(offer.getId(), offer);
    }

    public void removeOffer(long offerId) {
        this.offers.remove(offerId);
    }

    @Override
    public String getDisplayName() {
        return "offers_data";
    }

    @Override
    public String getPath() {
        return OxygenHelperClient.getDataFolder() + "/client/world/trade/offers_client.dat";
    }

    @Override
    public long getSaveDelayMinutes() {
        return TradeConfig.DATA_SAVE_DELAY_MINUTES.getIntValue();
    }

    @Override
    public void read(BufferedInputStream bis) throws IOException {
        int amount = StreamUtils.readInt(bis);
        PlayerOfferClient offer;
        for (int i = 0; i < amount; i++) {
            offer = new PlayerOfferClient();
            offer.read(bis);
            this.addOffer(offer);
        }
        TradeMain.LOGGER.info("Loaded <{}> trade offers.", this.getOffersAmount());
    }

    @Override
    public void write(BufferedOutputStream bos) throws IOException {
        StreamUtils.write(this.offers.size(), bos);
        for (PlayerOfferClient offer : this.offers.values())
            offer.write(bos);
    }

    @Override
    public void reset() {
        this.offers.clear();
    }
}