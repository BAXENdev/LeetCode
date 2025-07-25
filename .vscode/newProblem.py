
import os
import sys

def main():
    if (len(sys.argv) != 3):
        print("The program takes arguments: 1:workspaceFolderPath 2:problemNumber")
        sys.exit(1)
    
    workspaceFolderPath: str = sys.argv[1]
    problemNumber: str = sys.argv[2]
    solutionFilePath: str = os.path.join(workspaceFolderPath, ".vscode", "Solution.java")
    testFilePath: str = os.path.join(workspaceFolderPath, ".vscode", "SolutionTest.java")
    problemFolderPath: str = os.path.join(workspaceFolderPath, f"problem{problemNumber}")
    testFolderPath: str = os.path.join(problemFolderPath, "test")
    newSolutionFilePath: str = os.path.join(problemFolderPath, "Solution.java")
    newTestFilePath: str = os.path.join(testFolderPath, "SolutionTest.java")

    if (os.path.exists(problemFolderPath)):
        print("Problem folder already exists")
        sys.exit(1)

    solutionFileContent: str
    testFileContent: str
    with open(solutionFilePath, "r") as f:
        solutionFileContent = f.read()
    with open(testFilePath, "r") as f:
        testFileContent = f.read()
    solutionFileContent = solutionFileContent.replace("####", problemNumber)
    testFileContent = testFileContent.replace("####", problemNumber)

    os.mkdir(problemFolderPath)
    os.mkdir(testFolderPath)
    with open(newSolutionFilePath, "w") as f:
        f.write(solutionFileContent)
    with open(newTestFilePath, "w") as f:
        f.write(testFileContent)

if __name__ == "__main__":
    main()