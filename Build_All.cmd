set ant="D:\eclipse\plugins\org.apache.ant_1.7.1.v20100518-1145\bin\ant"
set nant="D:\nant-0.91-alpha1\bin\NAnt.exe"
set installjammer="C:\Program Files (x86)\InstallJammer\installjammer.exe"
set svn="C:\Program Files (x86)\VisualSVN Server\bin\svn.exe"
set zip="C:\Program Files (x86)\7-Zip\7z.exe"

cd Ares.Controller\Ares.Controller\ant
call %ant% -DProductVersion=%1 clean prepareSetup
cd ..\..\..

%nant% -D:ProductVersion=%1 clean prepareSetup


%installjammer% -DProductVersion %1 --build-dir build\temp --build-log-file build\setup.log --output-dir build\output --build-for-release --build Setup\Ares\Ares.mpi

%svn% export file:///D:/Repositories/Ares/trunk build\Ares_%1_Source
cd build
%zip% a output\Ares_%1_Source.7z Ares_%1_Source
