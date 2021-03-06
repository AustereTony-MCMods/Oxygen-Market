package austeretony.oxygen_market.client.sync;

import austeretony.oxygen_core.client.sync.DataSyncHandlerClient;
import austeretony.oxygen_core.client.sync.DataSyncListener;
import austeretony.oxygen_market.client.MarketManagerClient;
import austeretony.oxygen_market.client.gui.market.MarketScreen;
import austeretony.oxygen_market.common.main.MarketMain;
import austeretony.oxygen_market.common.market.SalesHistoryEntry;

import javax.annotation.Nullable;
import java.util.Map;

public class SalesHistorySyncHandlerClient implements DataSyncHandlerClient<SalesHistoryEntry> {

    @Override
    public int getDataId() {
        return MarketMain.DATA_ID_MARKET_HISTORY;
    }

    @Override
    public Class<SalesHistoryEntry> getSynchronousEntryClass() {
        return SalesHistoryEntry.class;
    }

    @Override
    public Map<Long, SalesHistoryEntry> getDataMap() {
        return MarketManagerClient.instance().getSalesHistoryMap();
    }

    @Override
    public void clear() {
        MarketManagerClient.instance().getSalesHistoryMap().clear();
    }

    @Override
    public void save() {
        MarketManagerClient.instance().markChanged();
    }

    @Nullable
    @Override
    public DataSyncListener getSyncListener() {
        return updated -> MarketScreen.salesHistoryDataSynchronized();
    }
}
