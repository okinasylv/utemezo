import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    char lastTask;
    String runstatistics = "";
    int time=0;
    ArrayList<WaitTask> wtarray = new ArrayList<>();
    ArrayList<ScheduleTask> rr = new ArrayList<>();
    int rrtasktime=0;
    int rrstatusch = 0;
    ArrayList<ScheduleTask> srtf = new ArrayList<>();
    int srtfstatusch = 0;

    ArrayList<Statistics> starray = new ArrayList<>();


    public void Init(){
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        try{
            while((line = br.readLine()) != null) {
                if(line!=""){
                    String[] temp = line.split(",");
                    if(temp.length != 4) throw(new Exception("Bad input"));
                    WaitTask wt = new WaitTask(temp[0].charAt(0), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), Integer.parseInt(temp[3]));
                    wtarray.add(wt);
                }
            }
        }catch(Exception e){ System.out.println(e.getMessage());}

        wtarray.sort(new CompareTasks());
        for (int i= 0; i < wtarray.size(); i++){
            System.out.println(wtarray.get(i).betu);
        }

    }

    public void utemezofeltolt(){
        if(wtarray.size()==0){
            return;
        }
        WaitTask w;
        while(wtarray.size()>0 && ((w = wtarray.get(0)).st == time)){
            if(w.pri==1){
                rr.add(new ScheduleTask(w.betu, w.cpu, 0));
            }
            else {
                srtf.add(new ScheduleTask(w.betu, w.cpu, 0));
                //TODO: ujrarendezes
            }
            wtarray.remove(0);
        }
    }

    //round-robin ütemezés
    public void rr(){

        if(rr.get(rrstatusch).marcpu == 0){
            starray.add(new Statistics(rr.get(rrstatusch).betu, rr.get(rrstatusch).varakozas));
            if(rr.get(rrstatusch).betu != lastTask){
                runstatistics += rr.get(rrstatusch).betu;
            }
            rr.remove(rrstatusch);

        }

        //ha 2egységnyit volt adott tasknál, akkor taskot vált
        if(rrtasktime>=2){
            rrstatusch++;
            rrtasktime=0;
            if(rrstatusch > rr.size()-1){
                rrstatusch=0;
            }
        }

        if(rr.size() != 0){
            rr.get(rrstatusch).marcpu--;
            rr.get(rrstatusch).varakozas--;
            lastTask = rr.get(rrstatusch).betu;
        }

    }

    public void srtf(){
//TODO
    }



    public static void main(String[] args) {
        Main m = new Main();
        m.Init();
        while(m.wtarray.size()!=0 || m.rr.size()!=0 || m.srtf.size()!=0){
            m.utemezofeltolt();

            if(m.rr.size() > 0){
                m.rr();
            }else if(m.srtf.size() > 0){
                    m.srtf();
                }

            //taskok várakozásának növelése
            for(int i = 0; i < m.rr.size(); i++){
                m.rr.get(i).varakozas++;
            }
            for(int i = 0; i < m.srtf.size(); i++){
                m.srtf.get(i).varakozas++;
            }


            m.time++;
        }
        System.out.println(m.runstatistics);
        //TODO: statisztikák kiírogatása
        //sorba rendezés!!!
        for(int i = 0; i < m.starray.size(); i++){
            System.out.print(m.starray.get(i).betu);
            System.out.println(":");
            System.out.print(m.starray.get(i).time);
        }
    }
}