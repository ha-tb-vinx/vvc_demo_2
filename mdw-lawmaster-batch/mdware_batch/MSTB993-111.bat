@echo off
rem =======================================
rem MSTB992-111 POS�pIF��ʃ}�X�^�쐬
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB993-111 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
