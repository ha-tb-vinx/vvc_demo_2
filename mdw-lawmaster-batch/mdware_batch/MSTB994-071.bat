@echo off
rem =======================================
rem MSTB994-071 �w���POS�p�t�@�C���쐬
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB994-071 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
