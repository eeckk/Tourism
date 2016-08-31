package jingdian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tools.ToastUtil;
import tourism.main.R;

import com.amap.api.cloud.model.AMapCloudException;
import com.amap.api.cloud.model.CloudItem;
import com.amap.api.cloud.model.CloudItemDetail;
import com.amap.api.cloud.model.LatLonPoint;
import com.amap.api.cloud.search.CloudResult;
import com.amap.api.cloud.search.CloudSearch;
import com.amap.api.cloud.search.CloudSearch.OnCloudSearchListener;
import com.amap.api.cloud.search.CloudSearch.SearchBound;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolygonOptions;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;

public class JingDianActivity extends Activity implements OnMarkerClickListener,
		InfoWindowAdapter, OnCloudSearchListener, OnInfoWindowClickListener,
		LocationSource ,AMapLocationListener {
	private MapView mapView;
	private AMap mAMap;
	private CloudSearch mCloudSearch;
	private String mTableID = "55213105e4b0b982d3accfce"; // 用户tableid，从官网下载测试数据后在云图中新建地图并导入，获取相应的tableid
	private String mId = ""; // 用户table 行编号
	private String mKeyWord = "景点"; // 搜索关键字
	private CloudSearch.Query mQuery;
	private LatLonPoint mCenterPoint; // 周边搜索中心点
	private LatLonPoint mPoint1;
	private LatLonPoint mPoint2;
	private LatLonPoint mPoint3;
	private LatLonPoint mPoint4;
	private PoiOverlay_YunTu mPoiCloudOverlay;
	private List<CloudItem> mCloudItems;
	private ProgressDialog progDialog = null;
	private Marker mCloudIDMarer;
	private String TAG = "AMapYunTuDemo";
	private OnLocationChangedListener mListener;
	int juli=40000000;

	private ArrayList<CloudItem> items = new ArrayList<CloudItem>();
	private LocationManagerProxy mAMapLocationManager;
	private boolean flag=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yuntu);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
		setUpMap();
	}

	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		mAMap.setLocationSource(this);// 设置定位监听
		mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
		mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
	}
	public void searchByBound() {
		showProgressDialog();
		items.clear();
		SearchBound bound = new SearchBound(new LatLonPoint(
				mCenterPoint.getLatitude(), mCenterPoint.getLongitude()), juli);
		try {
			mQuery = new CloudSearch.Query(mTableID, mKeyWord, bound);
			mQuery.setPageSize(10);
			CloudSearch.Sortingrules sorting = new CloudSearch.Sortingrules("_id",
					false);
			mQuery.setSortingrules(sorting);
			mCloudSearch.searchCloudAsyn(mQuery);// 异步搜索
		} catch (AMapCloudException e) {
			e.printStackTrace();
		}
		
	}



	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (mAMap == null) {
			mAMap = mapView.getMap();
		}
		mCloudSearch = new CloudSearch(this);
		mCloudSearch.setOnCloudSearchListener(this);
		mAMap.setOnMarkerClickListener(this);
		mAMap.setOnInfoWindowClickListener(this);
		mAMap.setInfoWindowAdapter(this);
		mAMap.setOnInfoWindowClickListener(this);

	}

	/**
	 * 显示进度框
	 */
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("正在搜索..." );
		progDialog.show();
	}

	/**
	 * 隐藏进度框
	 */
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onCloudItemDetailSearched(CloudItemDetail item, int rCode) {
		dissmissProgressDialog();// 隐藏对话框
		if (rCode == 0 && item != null) {
			if (mCloudIDMarer != null) {
				mCloudIDMarer.destroy();
			}
			mAMap.clear();
			LatLng position = AMapUtil_YunTu.convertToLatLng(item.getLatLonPoint());
			mAMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(new CameraPosition(position, 18, 0, 30)));
			mCloudIDMarer = mAMap.addMarker(new MarkerOptions()
					.position(position)
					.title(item.getTitle())
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
			items.add(item);
			Iterator iter = item.getCustomfield().entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				Log.d(TAG, key + "   " + val);
			}
		}

	}

	@Override
	public void onCloudSearched(CloudResult result, int rCode) {
		dissmissProgressDialog();

		if (rCode == 0) {
			if (result != null && result.getQuery() != null) {
				if (result.getQuery().equals(mQuery)) {
					mCloudItems = result.getClouds();

					if (mCloudItems != null && mCloudItems.size() > 0) {
						mAMap.clear();
						mPoiCloudOverlay = new PoiOverlay_YunTu(mAMap, mCloudItems);
						mPoiCloudOverlay.removeFromMap();
						mPoiCloudOverlay.addToMap();
						// mPoiCloudOverlay.zoomToSpan();
						for (CloudItem item : mCloudItems) {
							items.add(item);
							Iterator iter = item.getCustomfield().entrySet()
									.iterator();
							while (iter.hasNext()) {
								Map.Entry entry = (Map.Entry) iter.next();
								Object key = entry.getKey();
								Object val = entry.getValue();
								Log.d(TAG, key + "   " + val);
							}
						}
						if (mQuery.getBound().getShape()
								.equals(SearchBound.BOUND_SHAPE)) {// 圆形
							mAMap.addCircle(new CircleOptions()
									.center(new LatLng(mCenterPoint
											.getLatitude(), mCenterPoint
											.getLongitude())).radius(5000)
									.strokeColor(
									// Color.argb(50, 1, 1, 1)
											Color.RED)
									.fillColor(Color.argb(50, 1, 1, 1))
									.strokeWidth(25));

							mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
									new LatLng(mCenterPoint.getLatitude(),
											mCenterPoint.getLongitude()), 12));

						} else if (mQuery.getBound().getShape()
								.equals(SearchBound.POLYGON_SHAPE)) {
							mAMap.addPolygon(new PolygonOptions()
									.add(AMapUtil_YunTu.convertToLatLng(mPoint1))
									.add(AMapUtil_YunTu.convertToLatLng(mPoint2))
									.add(AMapUtil_YunTu.convertToLatLng(mPoint3))
									.add(AMapUtil_YunTu.convertToLatLng(mPoint4))
									.fillColor(Color.LTGRAY)
									.strokeColor(Color.RED).strokeWidth(1));
							LatLngBounds bounds = new LatLngBounds.Builder()
									.include(AMapUtil_YunTu.convertToLatLng(mPoint1))
									.include(AMapUtil_YunTu.convertToLatLng(mPoint2))
									.include(AMapUtil_YunTu.convertToLatLng(mPoint3))
									.build();
							mAMap.moveCamera(CameraUpdateFactory
									.newLatLngBounds(bounds, 50));
						} else if ((mQuery.getBound().getShape()
								.equals(SearchBound.LOCAL_SHAPE))) {
							mPoiCloudOverlay.zoomToSpan();
						}

					} else {
						ToastUtil.show(this, R.string.no_result);
					}
				}
			} else {
				ToastUtil.show(this, R.string.no_result);
			}
		} else {
			ToastUtil.show(this, R.string.error_network);
		}

	}

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		return false;
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		String tile = arg0.getTitle();
		for (CloudItem item : items) {
			if (tile.equals(item.getTitle())) {
				Intent intent = new Intent(JingDianActivity.this, InfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(InfoActivity.POIID, item.getID());
				bundle.putString(InfoActivity.POINAME, item.getTitle());
				bundle.putString(InfoActivity.POILOCATION, "{"
						+ item.getLatLonPoint().getLatitude() + ","
						+ item.getLatLonPoint().getLatitude() + "}");
				bundle.putString(InfoActivity.POIADDRESS, item.getSnippet());
				bundle.putString(InfoActivity.POICREATETIME,
						item.getCreatetime());
				bundle.putString(InfoActivity.POIUPDATETIME,
						item.getUpdatetime());
				bundle.putString(InfoActivity.POIDISTANCE,
						"" + item.getDistance());
				HashMap<String, String> customField = item.getCustomfield();
				ArrayList<String> keys = new ArrayList<String>();
				ArrayList<String> values = new ArrayList<String>();
				for (Entry<String, String> map : customField.entrySet()) {
					keys.add(map.getKey());
					values.add(map.getValue());
					
				}
				bundle.putStringArrayList(InfoActivity.POIKEY, keys);
				bundle.putStringArrayList(InfoActivity.POIVALUE, values);
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			}

		}

	}

	/**
	 * 
	 * 返回键监听
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			finish();

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO Auto-generated method stub
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getAMapException().getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
				mCenterPoint=new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
				
				if(flag){
				searchByBound();
				flag=false;
				}
			} else {
				Log.e("AmapErr","Location ERR:" + amapLocation.getAMapException().getErrorCode());
			}
		}
	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub处理定位更新
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用destroy()方法
			// 其中如果间隔时间为-1，则定位只定一次
			// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork, 1000, 10, this);
		}
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;
	}
}
