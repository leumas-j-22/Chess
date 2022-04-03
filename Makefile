# "make" to compile
# "make chess" to run

JAVAC = javac
JAVA = java

SRCDIR = src
OUTDIR = bin

JFLAGS = -d $(OUTDIR)
RM = rm

# Compile the java source files and place the resulting class files in the bin directory.
# Will create the bin directory if it does not already exist.
compile:
	@$(JAVAC) $(JFLAGS) $(SRCDIR)/chess/*.java $(SRCDIR)/chess/Pieces/*.java

# Run chess
chess:
	@$(JAVA) -cp $(OUTDIR) chess/Chess

# Remove all class files from the bin directory, but keep the bin directory structure
clean:
	@$(RM) $(OUTDIR)/chess/*.class $(OUTDIR)/chess/Pieces/*.class

# Remove everything from the bin directory and delete the directory itself
clean_dir:
	@$(RM) -r $(OUTDIR)