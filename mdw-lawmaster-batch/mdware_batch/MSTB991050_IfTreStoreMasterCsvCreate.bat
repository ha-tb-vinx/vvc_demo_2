@echo off
rem =======================================
rem MSTB991050 TRE����IF�t�@�C���i�X�܃}�X�^�j�쐬
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB991050 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
