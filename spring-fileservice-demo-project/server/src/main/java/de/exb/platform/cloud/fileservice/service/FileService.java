package de.exb.platform.cloud.fileservice.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.validation.constraints.NotNull;

public interface FileService {

	@NotNull
	public OutputStream openForWriting(@NotNull final String aSessionId, @NotNull final String aPath,
			final boolean aAppend)
		throws FileServiceException;

	@NotNull
	public InputStream openForReading(@NotNull final String aSessionId, @NotNull final String aPath)
			throws FileServiceException;

	@NotNull
	public List<String> list(@NotNull final String aSessionId, @NotNull final String aPath)
			throws FileServiceException;

	public void delete(@NotNull final String aSessionId, @NotNull final String aPath, final boolean aRecursive)
			throws FileServiceException;

	public boolean exists(@NotNull final String aSessionId, @NotNull final String aPath)
			throws FileServiceException;

	public String getParent(@NotNull final String aSessionId, @NotNull final String aPath)
			throws FileServiceException;
}
