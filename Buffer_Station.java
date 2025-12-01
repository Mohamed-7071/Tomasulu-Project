public abstract class Buffer_Station {
    public int Busy;
    public int output;

    public boolean IsBusy(){
        return Busy == 1;
    }
    
    public abstract void publish(String tag);
    
    public abstract void run();

    public abstract void clear();
}