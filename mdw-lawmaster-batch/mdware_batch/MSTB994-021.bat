@echo off
rem =======================================
rem MSTB994-021 �w���POS�pIF�J�e�S���}�X�^�쐬
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB994-021 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
