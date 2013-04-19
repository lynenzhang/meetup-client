/**
 * galaxy inc.
 * meetup client for android
 */
package com.galaxy.meetup.client.android.content.cache;

/**
 * 
 * @author sihai
 *
 */
public abstract class CachedImageRequest extends ImageRequest {

	private String mCacheDir;
    private String mCacheFileName;
    
	public CachedImageRequest() {
	}

	private void buildCacheFilePath() {
		String s = getCanonicalDownloadUrl();
		long l = 0x3ffffffffffe5L;
		int i = s.length();
		for (int j = 0; j < i; j++)
			l = 31L * l + (long) s.charAt(j);

		mCacheDir = Integer.toHexString(i % 16);
		mCacheFileName = (new StringBuilder()).append(getCacheFilePrefix())
				.append(Long.toHexString(0xfffffffffffffffL & l >> 4))
				.toString();
	}

    public final String getCacheDir() {
        if(mCacheDir == null)
            buildCacheFilePath();
        return mCacheDir;
    }

    public final String getCacheFileName() {
        if(mCacheFileName == null)
            buildCacheFilePath();
        return mCacheFileName;
    }

    protected abstract String getCacheFilePrefix();

    public abstract String getCanonicalDownloadUrl();

    public abstract String getDownloadUrl();

    public String getUriForLogging() {
        return getDownloadUrl();
    }
}
