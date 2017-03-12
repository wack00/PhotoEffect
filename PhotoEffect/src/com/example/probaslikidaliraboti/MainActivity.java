package com.example.probaslikidaliraboti;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;




import com.example.Implementation.BlackDotsApplyEffect;
import com.example.Implementation.GreyScaleApplyEffect;
import com.example.Implementation.NegativeApplyEffect;
import com.example.Implementation.GammaApplyEffect;
import com.example.Implementation.OldBlackWhiteApplyEffect;
import com.example.Implementation.ReflectionApplyEffect;
import com.example.Implementation.SepiaToneApplyEffect;
import com.example.Implementation.SnowApplyEffect;
import com.example.Implementation.SunnyApplyEffect;
import com.example.Infrastructure.ApplyEffect;










import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends Activity {

		
	
	private ListView list = null;
	private ArrayAdapter<ApplyEffect> mAdapter = null;

	private List<ApplyEffect> items = new ArrayList<ApplyEffect>();
	////
	
	
	private static final int CAMERA_REQUEST = 1000;
	private static final int IMAGE_REQUEST = 1001;
	public ImageView imageView;
	public Bitmap photo;
	private String image = null;
	
	
	
	///
	public void addEffect(ApplyEffect ae) {
		items.add(ae);
	}
	
	
	private void loadEffects() {
		GammaApplyEffect g = new GammaApplyEffect(this);
		SnowApplyEffect snow = new SnowApplyEffect(this);
		SepiaToneApplyEffect sepia = new SepiaToneApplyEffect(this);
		NegativeApplyEffect d = new NegativeApplyEffect(this);
		ReflectionApplyEffect r = new ReflectionApplyEffect(this);
		BlackDotsApplyEffect b = new BlackDotsApplyEffect(this);
		GreyScaleApplyEffect grey = new GreyScaleApplyEffect(this);
		SunnyApplyEffect bri = new SunnyApplyEffect(this);
		OldBlackWhiteApplyEffect con = new OldBlackWhiteApplyEffect(this);
	}
	///
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo_activity);
        
        imageView = (ImageView) this.findViewById(R.id.imageView1);
        
        list = (ListView) findViewById(R.id.listView1);

        mAdapter = new ArrayAdapter<ApplyEffect>(this,
				android.R.layout.simple_list_item_1, items);
        
        this.loadEffects();
        
        list.setAdapter(mAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				ApplyEffect ae = MainActivity.this.items.get(position);
				MainActivity.this.changePhoto(ae);
			}

		});
		
		Toast t = Toast.makeText(this, "Take a pics or use one of ur album." , Toast.LENGTH_LONG);
		t.show();
 
        
    }
    
   public void changePhoto(ApplyEffect ae) {
		Bitmap changedPhoto = null;
		changedPhoto = ae.applyEffect(MainActivity.this.photo);
		MainActivity.this.imageView.setImageBitmap(changedPhoto);
		
	}
    

   public void share(View view){
	   Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
	    sharingIntent.setType("text/plain");
	    String shareBody = "Picture";
	    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subjecttt");
	    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
	    startActivity(Intent.createChooser(sharingIntent, "Share via"));
   }
    public void getPicture(View view) {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, 1);
	}
    
    public void firstImage() {
		if (image == null) {
			Toast t = Toast.makeText(this, "Take a picture First!",
					Toast.LENGTH_SHORT);
			t.show();
		} else {
			imageView.setImageBitmap(photo);
		}
	}

    public void getImage(View view) {
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			// photo.
			imageView.setImageBitmap(photo);
			this.photo = photo;
			image = "done";

		} else if (resultCode == RESULT_OK) {
			Uri chosenImageUri = data.getData();

			Bitmap mBitmap = null;

			BitmapFactory.Options bf = new BitmapFactory.Options();
			bf.inSampleSize = 8;

			try {
				mBitmap = BitmapFactory.decodeStream(getContentResolver()
						.openInputStream(chosenImageUri), null, bf);
				// mBitmap.
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			this.photo = mBitmap;
			image = "done";
			imageView.setImageBitmap(photo);

		}

	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            this.firstImage();
        }
        return super.onOptionsItemSelected(item);
    }

   
}
