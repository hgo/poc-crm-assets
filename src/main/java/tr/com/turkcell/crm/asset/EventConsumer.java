package tr.com.turkcell.crm.asset;

public interface EventConsumer
{
    void consume(String message);
}
