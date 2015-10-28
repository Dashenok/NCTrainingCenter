package JL3;


    public class LinkedVector {

        public class Nod {
            public double element;
            public Nod next;
            public Nod prev;

            public Nod(double element) {
                this.element = element;
            }
        }

        protected Nod head;
        protected int size;

        protected Nod goToElement(int index) {
            Nod result = head;
            int i=0;
            while (i != index) {
                result = result.next;
            }
            return result;
        }

        protected void insertElementBefore(Nod current, Nod newNod) {
            newNod.next = current;
            newNod.prev = current.prev;
            current.prev.next = newNod;
            current.prev = newNod;
        }

        protected void delElement(Nod current) {
            if (size == 1) {
                head = null;
            } else {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                if (current == head) {
                    head = current.next;
                }
            }
            size--;

        }

    }

