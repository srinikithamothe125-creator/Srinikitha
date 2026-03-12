import java.util.*;

public class EventManagementWebsite {

    static class Event {
        int id;
        String name;
        String venue;
        double price;

        Event(int id,String name,String venue,double price){
            this.id=id;
            this.name=name;
            this.venue=venue;
            this.price=price;
        }

        public String toString(){
            return id+" | "+name+" | "+venue+" | "+price;
        }
    }

    static class Node{
        Event data;
        Node next;

        Node(Event e){
            data=e;
        }
    }

    static class EventList{

        Node head;

        void addEvent(Event e){

            Node newNode=new Node(e);

            if(head==null){
                head=newNode;
                return;
            }

            Node temp=head;

            while(temp.next!=null)
                temp=temp.next;

            temp.next=newNode;
        }

        void displayEvents(){

            Node temp=head;

            while(temp!=null){
                System.out.println(temp.data);
                temp=temp.next;
            }
        }

        Event search(String name){

            Node temp=head;

            while(temp!=null){

                if(temp.data.name.equalsIgnoreCase(name))
                    return temp.data;

                temp=temp.next;
            }

            return null;
        }

        List<Event> toArray(){

            List<Event> list=new ArrayList<>();

            Node temp=head;

            while(temp!=null){
                list.add(temp.data);
                temp=temp.next;
            }

            return list;
        }
    }

    static void bubbleSort(List<Event> list){

        int n=list.size();

        for(int i=0;i<n-1;i++){

            for(int j=0;j<n-i-1;j++){

                if(list.get(j).price > list.get(j+1).price){

                    Event temp=list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1,temp);

                }
            }
        }
    }

    static Queue<Event> bookingQueue=new LinkedList<>();

    static Stack<Event> undoStack=new Stack<>();

    static HashMap<Integer,Event> eventMap=new HashMap<>();

    public static void main(String[] args){

        Scanner sc=new Scanner(System.in);

        EventList events=new EventList();

        events.addEvent(new Event(1,"Wedding","Grand Hall",50000));
        events.addEvent(new Event(2,"Birthday","Garden",15000));
        events.addEvent(new Event(3,"Conference","City Center",30000));
        events.addEvent(new Event(4,"Concert","Stadium",70000));

        eventMap.put(1,new Event(1,"Wedding","Grand Hall",50000));
        eventMap.put(2,new Event(2,"Birthday","Garden",15000));
        eventMap.put(3,new Event(3,"Conference","City Center",30000));
        eventMap.put(4,new Event(4,"Concert","Stadium",70000));

        while(true){

            System.out.println("\n==== EVENT MANAGEMENT SYSTEM ====");
            System.out.println("1. View Events");
            System.out.println("2. Search Event");
            System.out.println("3. Sort Events by Price");
            System.out.println("4. Book Event");
            System.out.println("5. Process Booking");
            System.out.println("6. Undo Booking");
            System.out.println("7. Exit");

            int choice=sc.nextInt();
            sc.nextLine();

            switch(choice){

                case 1:

                    events.displayEvents();
                    break;

                case 2:

                    System.out.print("Enter Event Name: ");
                    String name=sc.nextLine();

                    Event e=events.search(name);

                    if(e!=null)
                        System.out.println("Found: "+e);
                    else
                        System.out.println("Event not found");

                    break;

                case 3:

                    List<Event> list=events.toArray();

                    bubbleSort(list);

                    for(Event ev:list)
                        System.out.println(ev);

                    break;

                case 4:

                    System.out.print("Enter Event ID: ");
                    int id=sc.nextInt();

                    if(eventMap.containsKey(id)){

                        Event book=eventMap.get(id);

                        bookingQueue.add(book);

                        undoStack.push(book);

                        System.out.println("Booking Added: "+book.name);
                    }
                    else
                        System.out.println("Invalid Event");

                    break;

                case 5:

                    if(!bookingQueue.isEmpty()){

                        Event processed=bookingQueue.remove();

                        System.out.println("Booking Processed: "+processed.name);
                    }
                    else
                        System.out.println("No bookings");

                    break;

                case 6:

                    if(!undoStack.isEmpty()){

                        Event undo=undoStack.pop();

                        bookingQueue.remove(undo);

                        System.out.println("Undo Booking: "+undo.name);
                    }
                    else
                        System.out.println("Nothing to undo");

                    break;

                case 7:

                    System.exit(0);

                default:

                    System.out.println("Invalid choice");
            }
        }
    }
}