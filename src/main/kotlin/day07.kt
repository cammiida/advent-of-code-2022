import java.io.File
import kotlin.math.min

data class Directory(
    val name: String,
    val parentDir: Directory?,
    val files: MutableList<Int> = mutableListOf(),
    val subDirs: MutableList<Directory> = mutableListOf(),
) {
    override fun toString(): String {
        return """
            name = $name,
            parentDir = ${parentDir?.name},
            files = $files,
            subDirs = $subDirs
        """.trimIndent()
    }
}

fun getAccumulatedSize(dir: Directory): Int {
    val currentSize = dir.files.sum()
    val sumOfSubDirs = dir.subDirs.sumOf { getAccumulatedSize(it) }
    return currentSize + sumOfSubDirs
}

fun findDirsWithMaxSize(maxSize: Int, dir: Directory): List<Int> {
    val accumulatedSize = getAccumulatedSize(dir)
    val flatmappedAccumulatedSizes = dir.subDirs.flatMap { findDirsWithMaxSize(maxSize, it) }

    return if (accumulatedSize < maxSize) {
        listOf(accumulatedSize, *flatmappedAccumulatedSizes.toTypedArray())
    }
    else {
        flatmappedAccumulatedSizes
    }
}

fun findSmallestDirToDelete(dir: Directory, minSize: Int, currentSmallest: Int): Int {
    val accumulatedSize = getAccumulatedSize(dir)
    return if (accumulatedSize < minSize) {
        currentSmallest
    } else if (dir.subDirs.isNotEmpty()) {
        min(dir.subDirs.minOf { findSmallestDirToDelete(it, minSize, accumulatedSize) }, accumulatedSize)
    } else {
        accumulatedSize
    }
}

fun part1(rootDir: Directory) {
    println(findDirsWithMaxSize(100000, rootDir).sum())
}

fun part2(rootDir: Directory) {
    val totalDiskSpace = 70000000
    val totalUsedDiskSpace = getAccumulatedSize(rootDir)
    val diskspaceLeft = totalDiskSpace - totalUsedDiskSpace
    val diskSpaceNeeded = 30000000

    val minDiskSpaceToDelete = diskSpaceNeeded - diskspaceLeft

    print(findSmallestDirToDelete(rootDir, minDiskSpaceToDelete, 0))
}

fun main() {
    val input = File("./input/day07.txt").readLines()

    val rootDir = Directory(
        name ="/",
        files = mutableListOf(),
        subDirs = mutableListOf(),
        parentDir = null
    )
    var currentDir = rootDir
    input.forEach { line ->
        if (line.startsWith("$ cd")) {
            // if name in subdirs, currentdir = that subdir
            val targetDirName = line.split("$ cd ")[1]
            if (targetDirName == "..") {
                currentDir = currentDir.parentDir ?: rootDir
            } else {
                val targetDir = currentDir.subDirs.find { it.name == targetDirName }
                if (targetDir != null) {
                    currentDir = targetDir
                }
            }

        }
        // else create subdirs and files
        else if (line.startsWith("dir ")) {
            val dirName = line.split("dir ")[1]
            currentDir.subDirs.add(Directory(name = dirName, parentDir = currentDir))
        }
        else if (!line.startsWith("$") && !line.startsWith("dir")) {
            val fileSize = line.split(" ")[0].toInt()
            currentDir.files.add(fileSize)
        }
    }

    print("part 1: ")
    part1(rootDir)
    print("part 2: ")
    part2(rootDir)
}