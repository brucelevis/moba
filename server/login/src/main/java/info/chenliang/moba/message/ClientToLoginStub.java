package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public abstract class ClientToLoginStub {
protected abstract DataOutputStream begin();
protected abstract void end();
public void login(String username,String password) throws Exception{
DataOutputStream dos = begin();
dos.write(0);
dos.writeUTF(username);
dos.writeUTF(password);
end();
}
}
