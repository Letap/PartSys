package particles;

import java.util.LinkedList;

import org.lwjgl.util.vector.Vector3f;

public class BoxContainer {
	private Vector3f bottom;
	private Vector3f top;
	private Vector3f bounds;
	private int sections;
	@SuppressWarnings("rawtypes")
	private LinkedList[][][] data;

	@SuppressWarnings("rawtypes")
	public BoxContainer(Vector3f bottom, Vector3f top, int sections) {
		super();
		this.bottom = bottom;
		this.top = top;
		this.sections = sections;
		data = new LinkedList[sections+1][sections+1][sections+1];
		
		for(int i = 0;i<sections+1;i++){
			for(int j = 0;j<sections+1;j++){
				for(int k = 0;k<sections+1;k++){
					data[i][j][k] = new LinkedList<Particle>();
				}
			}
		}

		bounds =new Vector3f(top.x-bottom.x,top.y-bottom.y,top.z-bottom.z);
	}
	
	@SuppressWarnings("unchecked")
	public void addPoint(Particle p){
		Vector3f i = getIndex(p.getPosition());
			data[(int)i.getX()][(int)i.getY()][(int)i.getZ()].add(p);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateParticles(){
		
		for(int i = 0;i<sections+1;i++){
			for(int j = 0;j<sections+1;j++){
				for(int k = 0;k<sections+1;k++){
					LinkedList<Particle> pijk = (LinkedList<Particle>) data[i][j][k];
					for (int l = 0; l< pijk.size(); l++) {
						Particle pi = pijk.get(l);
						if(wrongIndex(pi,i,j,k)){
							data[i][j][k].remove(pi);
							if(getIndex(pi.getPosition()).getX() != -1){
								Vector3f newindex = getIndex(pi.getPosition());
								data[(int)newindex.getX()][(int)newindex.getY()][(int)newindex.getZ()].add(pi);
							}
						}
			        }
				}
			}
		}
	}
	
	public Vector3f getIndex(Vector3f position){
		if(position.x < bottom.x || position.y < bottom.y || position.z < bottom.z){
			//System.out.println("ERROR, BoxContainer: Can't find index, position out of bounds" );
			return(new Vector3f(-1,-1,-1));
		}
		if(position.x > top.x || position.y > top.y || position.z > top.z){
			//System.out.println("ERROR, BoxContainer: Can't find index, position out of bounds" );
			return(new Vector3f(-1,-1,-1));
		}
		Vector3f croppedPosition = new Vector3f((position.x-bottom.x)/bounds.x,(position.y-bottom.y)/bounds.y,(position.z-bottom.z)/bounds.y);
		Vector3f index = new Vector3f((int)(sections*croppedPosition.x),(int)(sections*croppedPosition.y),(int)(sections*croppedPosition.z));
		
		return index;
	}
	
	private boolean wrongIndex(Particle p, int i, int j, int k){
		Vector3f actual = getIndex(p.getPosition());
		if(actual.x<0.5f){
			return true;
		}
		if(i!=(int)actual.x ||j!=(int)actual.y ||k!=(int)actual.z){
			return true;
		}
		
		
		return false;
	}
	
	private boolean isOutOfBounds(Vector3f pi){
		//if(position.x<=minx) return true;
		//if(position.x>=maxx) return true;
		if(pi.y<=0) return true;
		//if(position.y>=maxy) return true;
		//if(position.z<=minz) return true;
		//if(position.z>=maxz) return true;
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<Particle> nearbyParticles(Particle p){
		LinkedList<Particle> ret = new LinkedList<Particle>();
		Vector3f index = getIndex(p.getPosition());
		if(index.getX()==-1) return ret;
		int i = (int) index.getX();
		int j = (int) index.getY();
		int k = (int) index.getZ();
		System.out.format("%d, %d, %d", i, j,k );  System.out.println();
		LinkedList<Particle> pijk = (LinkedList<Particle>) data[i][j][k];
		for (int l = 0; l< pijk.size(); l++) {
			Particle pi = pijk.get(l);
			ret.add(pi);
        }
		
		if(i>0){

			System.out.format("%d, %d, %d", i-1, j,k );  System.out.println();
			LinkedList<Particle> p2 = (LinkedList<Particle>) data[i-1][j][k];
			for (int l = 0; l< pijk.size(); l++) {
				Particle pi = pijk.get(l);
				ret.add(pi);
	        }
		}
		if(i<sections){

			System.out.format("%d, %d, %d", i+1, j,k );  System.out.println();
			LinkedList<Particle> p2 = (LinkedList<Particle>) data[i+1][j][k];
			for (int l = 0; l< pijk.size(); l++) {
				Particle pi = pijk.get(l);
				ret.add(pi);
	        }
		}

		if(j>0){
			LinkedList<Particle> p2 = (LinkedList<Particle>) data[i][j-1][k];
			for (int l = 0; l< pijk.size(); l++) {
				Particle pi = pijk.get(l);
				ret.add(pi);
	        }
		}
		if(j<sections){
			LinkedList<Particle> p2 = (LinkedList<Particle>) data[i][j+1][k];
			for (int l = 0; l< pijk.size(); l++) {
				Particle pi = pijk.get(l);
				ret.add(pi);
	        }
		}

		if(k>0){
			LinkedList<Particle> p2 = (LinkedList<Particle>) data[i][j][k-1];
			for (int l = 0; l< pijk.size(); l++) {
				Particle pi = pijk.get(l);
				ret.add(pi);
	        }
		}
		if(k<sections){
			LinkedList<Particle> p2 = (LinkedList<Particle>) data[i][j][k+1];
			for (int l = 0; l< pijk.size(); l++) {
				Particle pi = pijk.get(l);
				ret.add(pi);
	        }
		}
		
		
		
		return ret;
	}

}
