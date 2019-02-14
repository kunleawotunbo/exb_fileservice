package de.exb.platform.cloud.fileservice.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public OutputStream openForWriting(final String aSessionId, final String aPath, final boolean aAppend)
			throws FileServiceException {
		try {
			return new FileOutputStream(new File(aPath), aAppend);
		} catch (final FileNotFoundException e) {
			throw new FileServiceException("cannot open entry", e);
		}
	}

	@Override
	public InputStream openForReading(final String aSessionId, final String aPath) throws FileServiceException {
		try {
			return new FileInputStream(new File(aPath));
		} catch (final FileNotFoundException e) {
			throw new FileServiceException("cannot open entry", e);
		}
	}

	@Override
	public List<String> list(final String aSessionId, final String aPath) throws FileServiceException {
		final List<String> files = new ArrayList<>();
		for (final File f : new File(aPath).listFiles()) {
			files.add(f.getAbsolutePath());
		}
		return files;
	}

	@Override
	public void delete(final String aSessionId, final String aPath, final boolean aRecursive)
			throws FileServiceException {

		final File f = new File(aPath);
		if (aRecursive) {
			final Path path = Paths.get(f.getAbsolutePath());
			try {
				Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(final Path aFile, final BasicFileAttributes aAttrs)
							throws IOException {
						Files.delete(aFile);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(final Path aDir, final IOException aExc)
							throws IOException {
						Files.delete(aDir);
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (final IOException e) {
				throw new FileServiceException("cannot delete entries", e);
			}
		} else {
			if (!f.delete()) {
				throw new FileServiceException("cannot delete entry");
			}
		}
	}

	@Override
	public boolean exists(final String aSessionId, final String aPath) throws FileServiceException {
		return new File(aPath).exists();
	}

	@Override
	public String getParent(final String aSessionId, final String aPath) throws FileServiceException {
		final File f = new File(aPath).getParentFile();
		if (f == null) {
			return null;
		}
		return f.getAbsolutePath();
	}
}
