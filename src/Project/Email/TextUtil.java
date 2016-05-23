package Project.Email;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JTextArea;

public class TextUtil  extends JTextArea implements DropTargetListener{
	private DropTarget dropTarget;
	
	public TextUtil(){
		dropTarget=new DropTarget(this,DnDConstants.ACTION_COPY_OR_MOVE,this,true);
	}
	
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		   DataFlavor[] dataFlavors = dtde.getCurrentDataFlavors();  
	        if(dataFlavors[0].match(DataFlavor.javaFileListFlavor)){  
	            try {  
	                Transferable tr = dtde.getTransferable();  
	                Object obj = tr.getTransferData(DataFlavor.javaFileListFlavor);  
	                List<File> files = (List<File>)obj;  
	                for(int i = 0; i < files.size(); i++){
	                	
	                	
	                    append(files.get(i).getAbsolutePath()+"\r\n");  
	                }  
	            } catch (UnsupportedFlavorException ex) {  
	  
	            } catch (IOException ex) {  
	  
	            }  
	        }  
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}

}
