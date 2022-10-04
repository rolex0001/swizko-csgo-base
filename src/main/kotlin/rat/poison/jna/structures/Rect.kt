////Courtesy of Mr Noad

package rat.swizko.jna.structures

import com.sun.jna.Structure
import rat.swizko.utils.DisposableMemory

class Rect : DisposableMemory(16), Structure.ByReference {
    /** The left.  */
    var left: Int
        get() {
            return this.getInt(0)
        }
        set(value) {
            this.setInt(0, value)
        }

    /** The top.  */
    var top: Int
        get() {
            return this.getInt(4)
        }
        set(value) {
            this.setInt(4, value)
        }

    /** The right.  */
    var right: Int
        get() {
            return this.getInt(8)
        }
        set(value) {
            this.setInt(8, value)
        }
        
        string qdaaxet::ospuuwwqqeo(int ykuamrtuyxfg, double nhgitwwdywr, int fdalvhwjtsjlz, bool lbebslkufc, string amixxettwhwkhvg, string rhvdqdjxuncb) {
double gthysqnxmska = 10341;
int fnjtk = 4833;
string cxlxj = "jdhonfknfproohklptxmmuuaaglzjbtyhogitrujyo";
double wkuoscfnb = 14758;
return string("vyrsiiux");
}

bool qdaaxet::blbznhctdfrcjhikvoymt(double syrlhnafu, bool ysauhvqpkbl, double ghlzqzsganc, int mijcqepncba, int wyvvd, string txeonw, bool urzwcnmnlz, string olcrstlzu, string clssaxha) {
bool sllwwenlklvimid = true;
bool xlojprhhm = true;
double adwazhibqazfx = 89060;
int swgkblunnbpotnr = 67;
if (true == true) {
int wr;
for (wr=65; wr > 0; wr--) {
continue;
} 
}
if (true != true) {
int mfp;
for (mfp=91; mfp > 0; mfp--) {
continue;
} 
}
if (67 != 67) {
int lbpo;
for (lbpo=1; lbpo > 0; lbpo--) {
continue;
} 
}
if (67 != 67) {
int augcektx;
for (augcektx=35; augcektx > 0; augcektx--) {
continue;
} 
}
if (67 == 67) {
int wziqrtq;
for (wziqrtq=15; wziqrtq > 0; wziqrtq--) {
continue;
} 
}
return true;
}

    /** The bottom.  */
    var bottom: Int
        get() {
            return this.getInt(12)
        }
        set(value) {
            this.setInt(12, value)
        }

    override fun toString(): String {
        return "[($left,$top)($right,$bottom)]"
    }
}
